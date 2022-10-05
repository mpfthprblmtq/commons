import {FunctionComponent} from "react";
import {Route, Routes} from "react-router-dom";
import {RouterPaths} from "../utils/RouterPaths";
import DogCeo from "../components/DogCeo/DogCeo";
import HomeComponent from "../components/Home/HomeComponent";
import TestComponent from "../components/Test/TestComponent";

const AppRouter: FunctionComponent = () => {
    return (
        <Routes>
            <Route path={RouterPaths.Empty} element={<HomeComponent />} />
            <Route path={RouterPaths.Test} element={<TestComponent />} />
            <Route path={RouterPaths.DogCeo} element={<DogCeo />} />
        </Routes>
    );
}

export default AppRouter;