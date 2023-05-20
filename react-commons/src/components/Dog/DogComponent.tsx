import {FunctionComponent, useEffect, useState} from "react";
import {RouterPaths} from "../../utils/RouterPaths";
import {StyledImg} from "./DogComponent.styles";
import {useDogAxiosInstance} from "../../services/useDogService";
import {CenteredDiv} from "../../app/App.styles";

const DogComponent: FunctionComponent = () => {
    const { getRandomDogImage } = useDogAxiosInstance();
    const [imageUrl, setImageUrl] = useState<string>('');

    useEffect(() => {
        getRandomDogImage().then((response) => setImageUrl(response));
        // eslint-disable-next-line
    }, []);

    return (
        <CenteredDiv>
            <h1>Hello from the Dog CEO Component!</h1>
            <p>Here's a random dog photo!</p>
            <StyledImg src={imageUrl} alt="random-dog" />
            <br/>
            <a href={RouterPaths.Empty} >Back to Home</a>
        </CenteredDiv>
    );
};

export default DogComponent;