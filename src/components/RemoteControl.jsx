import React, { useState } from 'react';
import { makeStyles } from '@material-ui/core/styles';
import remoteImgPath from '../assets/images/amazon_remote.png';
import ImageMapper from 'react-image-mapper';
import { ContactSupportTwoTone } from '@material-ui/icons';


const ThemeColor = "#8ea3ff";
const useStyles = makeStyles((theme) => ({
    remoteImageAlign: {
        margin: "0 auto",
    },
    mapHover: {
        position: "absolute",
        display: "block",
        backgroundColor: "black",
        color: "white", zIndex: 1
    }
}));

const MAP = {
    name: "my-map",
    areas: [
        { name: "Power Key", shape: "circle", coords: [53, 33, 10], fillColor: "#5b76ec", fillOpacity: 0.8 },
        { name: "Mic Key", shape: "circle", coords: [92, 63, 13], fillColor: "#5b76ec", fillOpacity: 0.8 },
        { name: "Back Key", shape: "circle", coords: [53, 215, 15], fillColor: "#5b76ec", fillOpacity: 0.8 },
        { name: "Home Key", shape: "circle", coords: [93, 215, 15], fillColor: "#5b76ec", fillOpacity: 0.8 },
        { name: "Menu Key", shape: "circle", coords: [130, 215, 15], fillColor: "#5b76ec", fillOpacity: 0.8 },
        { name: "Forward Key", shape: "circle", coords: [130, 255, 15], fillColor: "#5b76ec", fillOpacity: 0.8 },
        { name: "Play/Pause Key", shape: "circle", coords: [93, 255, 15], fillColor: "#5b76ec", fillOpacity: 0.8 },
        { name: "Backward Key", shape: "circle", coords: [53, 255, 15], fillColor: "#5b76ec", fillOpacity: 0.8 },
        { name: "Increase Key", shape: "circle", coords: [92, 290, 12], fillColor: "#5b76ec", fillOpacity: 0.8 },
        { name: "Decrease Key", shape: "circle", coords: [92, 329, 12], fillColor: "#5b76ec", fillOpacity: 0.8 },
        { name: "Mute Key", shape: "circle", coords: [93, 369, 15], fillColor: "#5b76ec", fillOpacity: 0.8 },
        { name: "Top Arrow Key", shape: "poly", coords: [50, 107, 62, 95, 89, 85, 114, 93, 128, 103, 116, 110, 89, 100, 62, 115], fillColor: "#5b76ec", fillOpacity: 0.8 },
        { name: "Right Arrow Key", shape: "poly", coords: [68, 166, 59, 178, 97, 193, 131, 171, 115, 163, 95, 174], fillColor: "#5b76ec", fillOpacity: 0.8 },
        { name: "Right Arrow Key", shape: "poly", coords: [118, 118, 125, 111, 135, 111, 143, 137, 138, 161, 135, 165, 120, 157, 122, 153, 126, 138], fillColor: "#5b76ec", fillOpacity: 0.8 },
        { name: "left Arrow Key", shape: "poly", coords: [45, 114, 59, 124, 57, 146, 65, 162, 51, 170, 39, 149, 40, 130], fillColor: "#5b76ec", fillOpacity: 0.8 },
    ]
}


export default function RemoteControl() {
    const cssclass = useStyles();


    function enterArea(area, e) {
        console.log(e)
    }

    function leaveArea(area) {

    }

    const clicked = (props, e) => {
        alert(props.name+" button clicked")

    }

    return (
        <div style={{ display: "flex", justifyContent: "center" }}>
            <ImageMapper id="ImageMap1" map={MAP} src={remoteImgPath}
                style={{ height: "500px", width: "178px" }}
                onClick={(area, evt) => clicked(area, evt)}
                onMouseEnter={(area, evt) => enterArea(evt)}
                alt="Fire Stick Remote" useMap="#workmap" />
        </div>
    )
}
