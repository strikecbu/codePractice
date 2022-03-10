import React, { useEffect, useState, useCallback } from 'react';

import Tasks from './components/Tasks/Tasks';
import NewTask from './components/NewTask/NewTask';
import useHttp from './hooks/use-http';

function App() {
  const { isLoading, error, sendRequest } = useHttp();

  const [tasks, setTasks] = useState([]);

  const fetchTasks = useCallback(async () => {
    const jsonData = await sendRequest({ url: 'https://react-http-68cbe-default-rtdb.asia-southeast1.firebasedatabase.app/tasks.json' });

    const loadedTasks = [];

    console.log(jsonData)

    for (const taskKey in jsonData) {
      loadedTasks.push({ id: taskKey, text: jsonData[taskKey].text });
    }

    setTasks(loadedTasks);
  }, [sendRequest]);

  useEffect(() => {
    fetchTasks();
  }, [fetchTasks]);

  const taskAddHandler = (task) => {
    setTasks((prevTasks) => prevTasks.concat(task));
  };

  return (
    <React.Fragment>
      <NewTask onAddTask={taskAddHandler} />
      <Tasks
        items={tasks}
        loading={isLoading}
        error={error}
        onFetch={fetchTasks}
      />
    </React.Fragment>
  );
}

export default App;
