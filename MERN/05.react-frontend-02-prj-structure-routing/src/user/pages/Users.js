import React from 'react';
import UsersList from '../components/UsersList';

const Users = () => {
  const USERS = [
    {
      id: 'u1',
      name: 'Andy Chen',
      image:
        'https://startuplatte.com/wp-content/uploads/2019/10/Andy-Puddicombe.jpg',
      places: 3,
    },
  ];
  return <UsersList items={USERS} />;
};

export default Users;
