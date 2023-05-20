import styled from "styled-components";
import {Button} from "@mui/material";

export const StyledText = styled.p`
  color: ${props => props.color || "blue"}
`;

export const StyledLink = styled.a`
    padding-bottom: 20px;
`;

export const StyledButton = styled(Button)`
  //color: tomato !important;
  background-color: cadetblue !important;
`;

export const CenteredDiv = styled.div`
  text-align: center;
`;