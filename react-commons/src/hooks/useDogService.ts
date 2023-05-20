import axios from "axios";

export interface DogServiceHooks {
    getRandomDogImage: () => Promise<string>;
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

    return { getRandomDogImage };
};

