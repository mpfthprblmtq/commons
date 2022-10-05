import React, {FunctionComponent} from "react";
import logo from "../../logo.svg";
import {RouterPaths} from "../../utils/RouterPaths";

const HomeComponent: FunctionComponent = () => {
    return (
        <div className="App">
            <header className="App-header">
                <img src={logo} className="App-logo" alt="logo" />
                <p>
                    Edit <code>src/app/App.tsx</code> and save to reload.
                </p>
                <a
                    className="App-link"
                    href={RouterPaths.DogCeo}
                    // target="_blank"
                    rel="noopener noreferrer">
                    Dog CEO
                </a>
                <a
                    className="App-link"
                    href={RouterPaths.Test}
                    // target="_blank"
                    rel="noopener noreferrer">
                    Test Component
                </a>
            </header>
        </div>
    );
}

export default HomeComponent;