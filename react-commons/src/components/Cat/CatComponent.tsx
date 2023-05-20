import {FunctionComponent, useEffect, useState} from "react";
import {RouterPaths} from "../../utils/RouterPaths";
import {StyledImg} from "./CatComponent.styles";
import {CenteredDiv} from "../../app/App.styles";
import {useCatAxiosInstance} from "../../services/useCatService";
import {CircularProgress} from "@mui/material";

const CatComponent: FunctionComponent = () => {
    const { getRandomCatImage } = useCatAxiosInstance();
    const [image, setImage] = useState<any>(null);

    useEffect(() => {
        getRandomCatImage()
            .then((response) => {
                // since the CatAAS API returns images in the form of blobs, we have to do some extra processing
                setImage(URL.createObjectURL(response));
            });
        // eslint-disable-next-line
    }, []);

    return (
        <CenteredDiv>
            <h1>Hello from the Cat Component!</h1>
            <p>Here's a random cat photo!</p>
            {image ? <StyledImg src={image} alt="random-cat" /> : <CircularProgress />}
            <br/>
            <a href={RouterPaths.Empty} >Back to Home</a>
        </CenteredDiv>
    );
};

export default CatComponent;