import axios from "axios";
import {capitalizeString} from "../utils/StringUtils";

export interface DogServiceHooks {
    getRandomDogImage: () => Promise<string>;
    getAllDogBreeds: () => Promise<string[]>;
    getRandomImageOfSpecificBreed: (breed: string) => Promise<string>;
}

export const useDogAxiosInstance = (): DogServiceHooks => {
    const dogAxiosInstance = axios.create({
        baseURL: "https://dog.ceo/api",
        timeout: 1000,
        headers: {}
    });

    const getRandomDogImage = async (): Promise<string> => {
        return await dogAxiosInstance.get("/breeds/image/random")
            .then((response) => {
                return response.data.message;
            })
            .catch((error) => {
                console.log(error);
            });
    };

    const getAllDogBreeds = async (): Promise<string[]> => {
        return await dogAxiosInstance.get("/breeds/list/all")
            .then((response) => {
                let breeds: string[] = [];
                Object.entries(response.data.message).forEach(([key, value]) => {
                    let valueArr: string[] = value as string[];
                    if (valueArr && valueArr.length > 0) {
                        valueArr.forEach((valueInArr) => {
                            breeds.push(capitalizeString(key) + ' ' + capitalizeString(valueInArr));
                        });
                    } else {
                        breeds.push(capitalizeString(key));
                    }
                });
                return breeds;
            });
    };

    const getRandomImageOfSpecificBreed = async (breed: string): Promise<string> => {
        console.log(breed);
        return await dogAxiosInstance.get(
            "/breed/" + breed.replace(" ", "/").toLowerCase() + "/images/random")
            .then((response) => {
                return response.data.message;
            })
            .catch((error) => {
                console.log(error);
            });
    };

    return { getRandomDogImage, getAllDogBreeds, getRandomImageOfSpecificBreed };
};

