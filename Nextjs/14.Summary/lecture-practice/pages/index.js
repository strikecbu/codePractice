import MeetupList from "../components/meetups/MeetupList";

const DUMMY_DATA = [
  {
    id: 1,
    image:
      "https://rainieis.tw/wp-content/uploads/IMG_3355.jpg",
    title: "House",
    address: "No1, North Dt, Tainan",
  },
  {
    id: 2,
    image:
      "https://rainieis.tw/wp-content/uploads/IMG_3355.jpg",
    title: "House1",
    address: "No2, North Dt, Tainan",
  }
];

function MeetupListPage() {
  return <MeetupList meetups={DUMMY_DATA}/>;
}

export default MeetupListPage;
