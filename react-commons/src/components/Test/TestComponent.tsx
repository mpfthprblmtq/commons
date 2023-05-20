import {FunctionComponent} from "react";
import {RouterPaths} from "../../utils/RouterPaths";

const TestComponent: FunctionComponent = () => {
    return (
        <div>
            <p>Hello from the Test Component!</p>
            <a href={RouterPaths.Empty} >Back to Home</a>
        </div>
    );
};

export default TestComponent;