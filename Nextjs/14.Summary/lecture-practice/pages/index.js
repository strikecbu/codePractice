import { useEffect, useState } from "react";
import MeetupList from "../components/meetups/MeetupList";

const DUMMY_DATA = [
  {
    id: 1,
    image: "https://rainieis.tw/wp-content/uploads/IMG_3355.jpg",
    title: "House",
    address: "No1, North Dt, Tainan",
  },
  {
    id: 2,
    image: "https://rainieis.tw/wp-content/uploads/IMG_3355.jpg",
    title: "House1",
    address: "No2, North Dt, Tainan",
  },
];

function MeetupListPage(props) {
  return <MeetupList meetups={props.meetups} />;
}

// export const getServerSideProps = async (ctx) => {

//   console.log("execute");

//   return {
//     props: {
//       meetups: DUMMY_DATA,
//     },
//   }
// }

export const getStaticProps = async (ctx) => {
  //load from server

  console.log("execute");
  return {
    props: {
      meetups: DUMMY_DATA,
    },
    revalidate: 60
  };
};

export default MeetupListPage;
