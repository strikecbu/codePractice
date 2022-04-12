import { BrowserRouter, Route, Routes } from 'react-router-dom';
import App from '../App';
import Download from '../components/Download';

const AppRouter = () => {
  return (
    <BrowserRouter>
      <Routes>
        <Route path="/" element={<App />} />
      </Routes>
    </BrowserRouter>
  );
};

export default AppRouter;
