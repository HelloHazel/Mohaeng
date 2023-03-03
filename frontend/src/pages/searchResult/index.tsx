"use client";

import Image from "next/image";
import styles from "./index.module.css";
import Link from "next/link";
import React, { useState } from "react";

type PlaceData = {
  id: string;
  title: string;
  image: string;
};

function Place(): JSX.Element {
  const places: PlaceData[] = require("/public/data/keyword.json");
  return (
    <>
      <section className={styles.placeList}>
        <h2 className={styles.h2}>검색하신 결과 </h2>
        <ul className={styles.container}>
          {places?.map((place) => (
            <li className={styles.item} key={place.id}>
              <Link className={styles.Link} href={`/place/${place.id}`}>
                <Image
                  className={styles.img}
                  src={place.image}
                  alt={place.title}
                  width={250}
                  height={150}
                  style={{
                    overflow: "hidden",
                  }}
                />
                <p className={styles.title}>{place.title}</p>
              </Link>
            </li>
          ))}
        </ul>
      </section>
    </>
  );
}

export default Place;
