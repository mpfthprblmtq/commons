import {FunctionComponent} from "react";
import {RouterPaths} from "../../utils/RouterPaths";

const DogCeo: FunctionComponent = () => {
    return (
        <div>
            <p>Hello from the Dog CEO Component!</p>
            <a href={RouterPaths.Empty} >Back to Home</a>
        </div>
    )
}

export default DogCeo;