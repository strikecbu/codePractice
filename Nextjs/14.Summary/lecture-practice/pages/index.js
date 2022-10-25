import { MongoClient } from 'mongodb';
import MeetupList from '../components/meetups/MeetupList';
import environment from '../env/environment';

// const DUMMY_DATA = [
//     {
//         id: 1,
//         image: 'https://rainieis.tw/wp-content/uploads/IMG_3355.jpg',
//         title: 'House',
//         address: 'No1, North Dt, Tainan',
//     },
//     {
//         id: 2,
//         image: 'https://rainieis.tw/wp-content/uploads/IMG_3355.jpg',
//         title: 'House1',
//         address: 'No2, North Dt, Tainan',
//     },
// ];

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
    const username = environment.mongodb.username;
    const password = environment.mongodb.password;
    const url = `mongodb+srv://${username}:${password}@cluster0.t6wct.mongodb.net/?retryWrites=true&w=majority`;
    const client = await MongoClient.connect(url);

    const db = client.db('next-meetups');
    const collection = db.collection('meetups');
    const allMeetups = await collection.find().toArray();

    console.log('execute');
    return {
        props: {
            meetups: allMeetups.map((data) => {
                const dto = {
                    ...data,
                    id: data._id.toString(),
                };
                delete dto._id;
                return dto;
            }),
        },
        revalidate: 60,
    };
};

export default MeetupListPage;
