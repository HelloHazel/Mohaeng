package com.mohaeng.backend.place.service;

import com.mohaeng.backend.Image.AmazonS3Service;
import com.mohaeng.backend.member.domain.Member;
import com.mohaeng.backend.member.repository.MemberRepository;
import com.mohaeng.backend.place.domain.Place;
import com.mohaeng.backend.place.domain.Review;
import com.mohaeng.backend.place.dto.PlaceDTO;
import com.mohaeng.backend.place.dto.PlaceDetailsDto;
import com.mohaeng.backend.place.dto.PlaceRatingDto;
import com.mohaeng.backend.place.dto.response.PlaceDetailsResponse;
import com.mohaeng.backend.place.exception.PlaceNotFoundException;
import com.mohaeng.backend.place.repository.PlaceBookmarkRepository;
import com.mohaeng.backend.place.repository.PlaceRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.stereotype.Service;
import org.springframework.util.StopWatch;
import org.springframework.web.multipart.MultipartFile;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
@Slf4j
@RequiredArgsConstructor
public class PlaceService {

    private final PlaceRepository placeRepository;
    private final PlaceBookmarkRepository placeBookmarkRepository;
    private final MemberRepository memberRepository;
    private final AmazonS3Service amazonS3Service;

    @Value("${API_KEY}")
    private String API_KEY;
    private String fileUrl;

    private String getBaseUrl() {
        return "https://apis.data.go.kr/B551011/KorService1/areaBasedList1?serviceKey=" + API_KEY + "&pageNo=1&numOfRows=12100&MobileApp=AppTest&_type=xml&MobileOS=ETC&arrange=A&contentTypeId=12";
    }

    private String getBaseUrl2() {
        return "https://apis.data.go.kr/B551011/KorService1/detailCommon1?serviceKey=" + API_KEY + "&MobileOS=ETC&MobileApp=AppTest&_type=xml&contentId=&contentTypeId=12&&overviewYN=Y";
    }

    public void saveInitImage() throws IOException {
        // 1. 리소스 폴더에 있는 파일을 읽어들인다
        Resource resource = new ClassPathResource("initImage/everytrip.png");
        InputStream inputStream = resource.getInputStream();
        MultipartFile file = new MockMultipartFile("everytrip.png", "everytrip.png", "image/png", inputStream);

        // 2. 파일을 S3에 저장한다
        List<String> fileUrls = amazonS3Service.uploadFile(Collections.singletonList(file));

        // 3. 저장 후 url을 리턴받는다
        fileUrl = fileUrls.get(0);
    }


    //    @Scheduled(cron = "0 0 5 * * ?") #TODO
    public List<Place> getPlaces() throws IOException, ParserConfigurationException, SAXException {
        Document doc = getXmlDocument(getBaseUrl());
        List<Place> places = new ArrayList<>();
        NodeList nodeList = doc.getElementsByTagName("item");
        for (int i = 0; i < nodeList.getLength(); i++) {
            Node node = nodeList.item(i);
            if (node.getNodeType() == Node.ELEMENT_NODE) {
                Element element = (Element) node;
                String name = element.getElementsByTagName("title").item(0).getTextContent();
                String address = element.getElementsByTagName("addr1").item(0).getTextContent();
                String addr2 = element.getElementsByTagName("addr2").item(0).getTextContent();
                String areaCode = element.getElementsByTagName("areacode").item(0).getTextContent();
                String firstImage = element.getElementsByTagName("firstimage").item(0).getTextContent();
                String firstImage2 = element.getElementsByTagName("firstimage2").item(0).getTextContent();
                String mapX = element.getElementsByTagName("mapx").item(0).getTextContent();
                String mapY = element.getElementsByTagName("mapy").item(0).getTextContent();
                String sigunguCode = element.getElementsByTagName("sigungucode").item(0).getTextContent();
                String contentId = element.getElementsByTagName("contentid").item(0).getTextContent();
//                String overview = "";
//                String overview = getOverview(contentid);
                if (firstImage == null || firstImage.isEmpty()) {
                    firstImage = fileUrl;
                }

                if (address == null || address.isEmpty()) {
                    address = addr2;
                }
                Place place = new Place(name, address, areaCode, sigunguCode, contentId, firstImage, firstImage2, mapX, mapY);
                places.add(place);
            }
        }
        if (places.isEmpty()) {
            throw new PlaceNotFoundException("No place found.");
        }
        return places;
    }

    private String getOverview(String contentId) {
        String urlStr = getBaseUrl2().replace("contentId=", "contentId=" + contentId);
        Document doc;
        NodeList nodeList;
        String overviewText = "";
        String overview = "";
        List<String> excludedIds = Arrays.asList("2763773", "2784642", "2946071", "2930677", "2891338",
                "2725011", "2891349", "2777911", "2750886", "2946230",
                "2760807", "2930681");

        try {
            doc = getXmlDocument(urlStr);
            nodeList = doc.getElementsByTagName("item");
            if (nodeList.getLength() > 0) {
                Node node = nodeList.item(0);
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element element = (Element) node;
                    overviewText = element.getElementsByTagName("overview").item(0).getTextContent();
                }
            }
        } catch (Exception e) {
            log.info("Exception:", e);
            // retry
        }
        return overviewText;
    }

    private Document getXmlDocument(String urlStr) throws IOException, ParserConfigurationException, SAXException {
        URL url = new URL(urlStr);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");

        InputStream responseStream = connection.getInputStream();

        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db = dbf.newDocumentBuilder();

        return db.parse(responseStream);
    }

    public List<Place> getPlacesAll() { // TODO: 정렬(페이지네이션 구현해야함)
        return placeRepository.findAll();
    }

    public List<Place> getPlacesByAddress(String address) {
        String searchValue = address.substring(0, 2); // get the first two letters of address
        return placeRepository.findByAddressContainingIgnoreCase(searchValue);
    }

    public Place getPlaceByContentId(String contentId) throws IOException, ParserConfigurationException, SAXException {
        List<Place> places = getPlaces();
        for (Place place : places) {
            if (place.getContentId().equals(contentId)) {
                return place;
            }
        }
        return null;
    }

    public PlaceDTO toPlaceDTO(Place place) {
        return PlaceDTO.builder()
                .name(place.getName())
                .address(place.getAddress())
                .areaCode(place.getAreaCode())
                .sigunguCode(place.getSigunguCode())
                .firstImage(place.getFirstImage())
                .firstImage2(place.getFirstImage2())
                .mapX(place.getMapX())
                .mapY(place.getMapY())
                .contentId(place.getContentId())
                .build();
    }

    public List<String> getPlaceOverview(String contentId) {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();

        List<Place> places = placeRepository.findByContentId(contentId);
        List<String> overviews = new ArrayList<>();
        for (Place place : places) {
            if (place.getContentId().equals(contentId)) {
                String overview = getOverview(place.getContentId());
                overview = overview.replaceAll("<br>|<br >|< br>|<br />|</br>|<strong>|</ strong>", "");
                overviews.add(overview);
            }
        }
        stopWatch.stop();
        long totalTimeMillis = stopWatch.getTotalTimeMillis();
        System.out.println("total time : " + totalTimeMillis);
        return overviews;
    }

    public PlaceDetailsResponse getPlaceDetailsByContentId(String contentId, Member member) throws IOException, ParserConfigurationException, SAXException {
        Place currentPlace = null;
        Boolean isBookmark = false;

        List<Place> places = placeRepository.findByContentId(contentId);
        List<String> overviews = getPlaceOverview(contentId);
        List<PlaceDetailsDto> placeDetailsDtos = IntStream.range(0, places.size())
                .mapToObj(i -> {
                    Place place = places.get(i);
                    String overview = overviews.get(i);
                    return new PlaceDetailsDto(place.getId(), place.getName(), place.getAreaCode(), place.getFirstImage(), place.getContentId(), place.getMapX(), place.getMapY(), overview);
                })
                .collect(Collectors.toList());

        if (member != null){
            isBookmark = placeBookmarkRepository.existsPlaceBookmarkByMemberAndPlace(member, places.get(0));
        }

        PlaceDetailsResponse response = new PlaceDetailsResponse(placeDetailsDtos, isBookmark);
        return response;
    }

    public PlaceRatingDto getPlaceRating(Long placeId) {
        Place place = placeRepository.findById(placeId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid place id: " + placeId));
        List<Review> reviews = place.getReviewList();
        double averageRating = reviews.stream()
                .mapToDouble(review -> Double.parseDouble(review.getRating()))
                .average()
                .orElse(0);
        averageRating = Math.round(averageRating * 100.0) / 100.0;
        long reviewTotalElements = reviews.size();
        return new PlaceRatingDto(averageRating, reviewTotalElements);
    }

    public String getFirstImage() {
        return fileUrl;
    }
}
