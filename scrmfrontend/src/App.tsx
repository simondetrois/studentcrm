import { CssBaseline, ThemeProvider } from "@mui/material";
import { useState } from "react";
import { Provider } from "react-redux";
import { Route, Routes, BrowserRouter as Router } from "react-router-dom";
import { ToastContainer } from "react-toastify";
import Navbar from "./Components/Navbar";
import Courses from "./Pages/Courses";
import Home from "./Pages/Home";
import Students from "./Pages/Students";
import store from "./Store/store";
import { DarkMode } from "./Themes/DarkMode";
import { LightMode } from "./Themes/LightMode";

function App() {
  const [light, setLight] = useState<boolean>(true);

  return (
    <ThemeProvider theme={light ? LightMode : DarkMode}>
      <CssBaseline />
      <Router>
        <Provider store={store}>
          <Navbar setLightMode={setLight} lightMode={light} />
          <Routes>
            <Route path='/' element={<Home />} />
            <Route path='/students' element={<Students />} />
            <Route path='/courses' element={<Courses />} />
          </Routes>
        </Provider>
      </Router>
      <ToastContainer />
    </ThemeProvider>
  );
}

export default App;
