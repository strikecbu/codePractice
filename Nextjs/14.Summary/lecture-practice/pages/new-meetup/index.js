import { useRouter } from 'next/router';
import NewMeetupForm from '../../components/meetups/NewMeetupForm';

function NewMeetupPage() {
    const router = useRouter();
    const onAddMeetup = async (data) => {
        const response = await fetch('/api/new-meetup', {
            method: 'POST',
            body: JSON.stringify(data),
            headers: {
                'Content-Type': 'application/json',
            },
        });
        const body = await response.json();
        console.log(body);
        router.push('/')
    };

    return <NewMeetupForm onAddMeetup={onAddMeetup} />;
}

export default NewMeetupPage;
