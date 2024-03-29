import React, {FunctionComponent} from "react";
import logo from "../../logo.svg";
import {RouterPaths} from "../../utils/RouterPaths";
import {StyledButton, StyledLink, StyledText} from "../../app/App.styles";

const HomeComponent: FunctionComponent = () => {

    const alertFunction = (text: string) => {
        alert(text);
    }

    return (
        <div className="App">
            <header className="App-header">
                <img src={logo} className="App-logo" alt="logo" />
                <StyledText color="gray">
                    Edit any component and save to reload!
                </StyledText>
                <StyledLink
                    className="App-link"
                    href={RouterPaths.Test}
                    rel="noopener noreferrer">
                    Test Component
                </StyledLink>
                <StyledLink
                    className="App-link"
                    href={RouterPaths.Dog}
                    rel="noopener noreferrer">
                    Dogs
                </StyledLink>
                <StyledLink
                    className="App-link"
                    href={RouterPaths.Cat}
                    rel="noopener noreferrer">
                    Cats
                </StyledLink>
                <StyledButton variant="contained" onClick={() => alertFunction("Hooray!")}>Test Button</StyledButton>
            </header>
        </div>
    );
};

export default HomeComponent;