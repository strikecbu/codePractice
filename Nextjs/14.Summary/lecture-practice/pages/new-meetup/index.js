import NewMeetupForm from "../../components/meetups/NewMeetupForm";

function NewMeetupPage() {
  const onAddMeetup = (data) => {
    console.log(data);
  };

  return <NewMeetupForm onAddMeetup={onAddMeetup} />;
}

export default NewMeetupPage;
