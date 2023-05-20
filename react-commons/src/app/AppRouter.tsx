import {FunctionComponent} from "react";
import {Route, Routes} from "react-router-dom";
import {RouterPaths} from "../utils/RouterPaths";
import DogComponent from "../components/Dog/DogComponent";
import HomeComponent from "../components/Home/HomeComponent";
import TestComponent from "../components/Test/TestComponent";
import CatComponent from "../components/Cat/CatComponent";

const AppRouter: FunctionComponent = () => {
    return (
        <Routes>
            <Route path={RouterPaths.Empty} element={<HomeComponent />} />
            <Route path={RouterPaths.Test} element={<TestComponent />} />
            <Route path={RouterPaths.Dog} element={<DogComponent />} />
            <Route path={RouterPaths.Cat} element={<CatComponent />} />
        </Routes>
    );
}

export default AppRouter;