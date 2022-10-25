import { MongoClient, ObjectId } from 'mongodb';
import MeetupDetail from '../../components/meetups/MeetupDetail';
import environment from '../../env/environment';

function MeetupDetailPage(props) {
    return (
        <MeetupDetail
            image={props.meetupDetail.image}
            title={props.meetupDetail.title}
            address={props.meetupDetail.address}
            description={props.meetupDetail.description}
        ></MeetupDetail>
    );
}

export const getStaticPaths = async () => {
    const username = environment.mongodb.username;
    const password = environment.mongodb.password;
    const url = `mongodb+srv://${username}:${password}@cluster0.t6wct.mongodb.net/?retryWrites=true&w=majority`;
    const client = await MongoClient.connect(url);

    const db = client.db('next-meetups');
    const collection = db.collection('meetups');

    const result = await collection.find({}, { _id: 1 }).toArray();
    client.close();
    const allIdKeys = result
        .map((data) => {
            return data._id.toString();
        })
        .map((id) => {
            return {
                params: {
                    meetupId: id,
                },
            };
        });

    return {
        paths: allIdKeys,
        fallback: false,
    };
};

export const getStaticProps = async (ctx) => {
    const meetupId = ctx.params.meetupId;

    const username = environment.mongodb.username;
    const password = environment.mongodb.password;
    const url = `mongodb+srv://${username}:${password}@cluster0.t6wct.mongodb.net/?retryWrites=true&w=majority`;
    const client = await MongoClient.connect(url);

    const db = client.db('next-meetups');
    const collection = db.collection('meetups');

    const result = await collection.findOne({ _id: ObjectId(meetupId) });
    client.close();

    console.log(meetupId);
    return {
        props: {
            meetupDetail: {
                image: result.image,
                title: result.title,
                address: result.address,
                description: result.description,
            },
        },
    };
};

export default MeetupDetailPage;
