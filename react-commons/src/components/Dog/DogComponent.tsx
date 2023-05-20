import {FunctionComponent, useEffect, useState} from "react";
import {RouterPaths} from "../../utils/RouterPaths";
import {StyledImg} from "./DogComponent.styles";
import {useDogAxiosInstance} from "../../services/useDogService";
import {CenteredDiv} from "../../app/App.styles";
import {CircularProgress, FormControl, Grid, InputLabel, MenuItem, Select, SelectChangeEvent} from "@mui/material";

const DogComponent: FunctionComponent = () => {
    const { getRandomDogImage, getAllDogBreeds, getRandomImageOfSpecificBreed } = useDogAxiosInstance();
    const [imageUrl, setImageUrl] = useState<string>('');
    const [breeds, setBreeds] = useState<string[]>([])
    const [breed, setBreed] = useState<string>('');

    useEffect(() => {
        getAllDogBreeds().then(response => setBreeds(response));
        getRandomDogImage().then((response) => setImageUrl(response));
        // eslint-disable-next-line
    }, []);

    const handleBreedChange = async (event: SelectChangeEvent) => {
        setBreed(event.target.value);
        getRandomImageOfSpecificBreed(event.target.value).then(response => setImageUrl(response));
    }

    return (
        <CenteredDiv>
            <h1>Hello from the Dog CEO Component!</h1>
            <p>Here's a random dog photo!</p>
            <Grid container>
                <Grid item xs={4} />
                <Grid item xs={2}>
                    <p>Want a specific dog breed?</p>
                </Grid>
                <Grid item xs={2}>
                    <FormControl sx={{ m: 1, minWidth: 120 }}>
                        <InputLabel>Dog Breed</InputLabel>
                        <Select onChange={handleBreedChange} value={breed} label={"Dog Breed"}>
                            {breeds.map(option => {
                                return (
                                    <MenuItem key={option} value={option}>{option}</MenuItem>
                                );
                            })}
                        </Select>
                    </FormControl>
                </Grid>
                <Grid item xs={4} />
            </Grid>
            {imageUrl ? <StyledImg src={imageUrl} alt="random-dog" /> : <CircularProgress />}
            <br/>
            <a href={RouterPaths.Empty} >Back to Home</a>
        </CenteredDiv>
    );
};

export default DogComponent;