import MeetupDetail from "../../components/meetups/MeetupDetail";

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
  return {
    paths: [
      {
        params: {
          meetupId: "1",
        },
      },
      {
        params: {
          meetupId: "2",
        },
      },
    ],
    fallback: false,
  };
};

export const getStaticProps = async (ctx) => {
  const meetupId = ctx.params.meetupId;

  console.log(meetupId);
  return {
    props: {
      meetupDetail: {
        image: "https://rainieis.tw/wp-content/uploads/IMG_3355.jpg",
        title: `House${meetupId}`,
        address: "Some street No5. Some city",
        description: "This is a good place",
      },
    },
  };
};

export default MeetupDetailPage;
