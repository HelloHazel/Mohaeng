# 사용자가 만드는 국내여행 플랫폼, 모두의 여행 Mohaeng
![image](https://user-images.githubusercontent.com/94662639/232202485-4d2b4605-312a-406b-be3c-e396dd0f8972.png)




모두의 여행은 국내 여행 커뮤니티 플랫폼입니다

국내의 다양한 여행지를 검색하여 직접 여행 코스를 작성할 수 있고, 해당 일정을 다른 사용자들과 공유하며 국내 여행을 보다 더 편리하고 즐겁게 할 수 있는 서비스를 목표로 진행한 프로젝트입니다

## 📝 관련 링크 

(담당하신 부분 전부 현재 버전으로 바꿔주세요)

[Github](https://github.com/Mohaeng2023/Mohaeng)

[요구사항 및 기능정의서](https://docs.google.com/spreadsheets/d/1s9AVLdhSfp5DTqd-lz0jkD7A-DtikWaR5JPT4BtyXx8/edit#gid=0)

[API 문서](https://www.notion.so/API-ccec3cd5f7614c30b3f0c4d43d7e4a5c)

## 📌 프로젝트 소개

- **프로젝트 배경** : 사용자 경험에 초점을 맞춘 국내 여행 웹사이트의 필요성
- **프로젝트 목표** : 직접 일정을 작성하고 유저들과 공유하며 국내 여행을 보다 더 편리하고 즐겁게 할 수 있는 경험을 제공하자
- **개발기간** : 2023/02/01 ~ 2023/04/09 (약 2달)
- **인원** : 6명(FE 3명, BE 3명)


## 📙 기획 의도
![image](https://user-images.githubusercontent.com/94662639/232201100-dd23a0f9-5b6d-4b21-8b12-984aea1aabbd.png)

## 🧑🏻‍💻 사용 기술
![image](https://user-images.githubusercontent.com/94662639/232202389-3d32497b-1c6c-42f2-a17c-447ef2502510.png)
## 📘 협업 툴


- Notion
- Github
- Git
- Figma
- erdcloud
- draw.io

## 🌐 데이터베이스 구조


(수정 후 추가)



## 💡 주요 기능 
-담당 프론트엔드 분들 screenGif 프로그램 까셔서 녹화 움짤 + 기능 추가 작성 해주시면 됩니다.


### 메인페이지
- 모행에서 별점과 리뷰가 많이 달린 TOP10 여행지와 좋아요가 많은 코스 목록을 슬라이더로 보여주고 아이템을 클릭하면 해당 상세 페이지로 이동한다.
- 여행지, 코스, 마이페이지를 클릭하여 각 페이지로 이동할 수 있다.
- 메인 아이콘 클릭 시 홈페이지로 이동한다.

### 통합검색
![통합검색](https://user-images.githubusercontent.com/108039645/232226951-039a6d45-bae0-42e5-aabf-3beacd3e50ff.gif)
- 검색창에 단어를 입력하면 해당 단어가 포함된 검색 결과를 확인할 수 있다.
- 여행지와 코스 탭을 이동하면 해당하는 여행지 목록을 페이지네이션으로 보여준다.
- 해당 여행지의 별점과 리뷰 개수, 해당 코스의 좋아요 수를 목록에서 확인할 수 있다.
- 로그인한 사용자의 북마크 여부를 확인할 수 있다.

### 여행지 목록 페이지
![여행지 전체](https://user-images.githubusercontent.com/94662639/232213110-0fdd347b-729c-43c5-b00a-2f02483a4e76.gif)
- 지역 필터를 통해 검색하고 싶은 지역의 여행지를 볼 수 있다.
- 북마크 버튼을 통해 마이페이지 즐겨찾기 메뉴에서 사용자가 북마크한 여행지를 볼 수 있다. 비로그인시 북마크 버튼을 클릭하면 로그인 페이지로 이동한다.
- 페이지 이동을 통해 특정한 여행지를 효율적으로 찾을 수 있다.

### 여행지 상세
![여행지 상세](https://user-images.githubusercontent.com/108039645/232228575-a5fca674-774a-4b7f-bec8-69fb746b7842.gif)

- 해당 여행지의 사진과 세부정보를 확인할 수 있다. 여행지의 장소는 카카오 맵으로 표시한다.
- 북마크 버튼을 클릭하면 마이페이지에서 저장된 북마크 목록을 볼 수 있다. 로그인하지 않은 사용자가 북마크 클릭 시 로그인 페이지로 이동한다.
- 하단에는 평균 별점과 리뷰 목록을 볼 수 있고 정렬을 통해 최신순, 별점 높은 순으로 리뷰를 표시한다.
- 리뷰 아이템에서 더보기 버튼을 클릭하면 상세한 글과 첨부된 사진을 같이 조회할 수 있다.
- 로그인하지 않은 사용자가 리뷰 작성 버튼을 누르면 로그인 페이지로 이동한다.

### 여행지 리뷰 작성
![여행지 리뷰](https://user-images.githubusercontent.com/108039645/232230248-10f4057a-41a2-4b31-930d-48939e2466ab.gif)

- 별점과 리뷰내용을 입력하고, 이미지를 첨부하여 미리보기로 확인한 후 리뷰를 작성할 수 있다.
- 작성한 리뷰를 토대로 수정하고 삭제할 수 있다.

### 코스 목록 페이지

### 코스 작성 페이지


### 마이페이지
![마이페이지 전체](https://user-images.githubusercontent.com/94662639/232225826-ca25b0b2-6c32-4b5a-9c20-aac238b90115.gif)


## ✉️ Contact
- 강승구
- 장지혜 : jihye219219@gmail.com
- 홍석영 : binaryhong@gmail.com
- 김새롬 : sagesaeromkim@gmail.com
- 김정민 
- 유은경 : deaese94@gmail.com
