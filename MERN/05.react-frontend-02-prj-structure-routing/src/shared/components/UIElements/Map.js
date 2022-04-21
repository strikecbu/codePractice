import React, {useEffect, useRef} from 'react'
import './Map.css'

const Map = (props) => {
    const mapRef = useRef()

    const {zoom, center} = props;
    useEffect(() => {
        console.log('Gen map')
        const map = new window.google.maps.Map(mapRef.current, {
            center: center,
            zoom: zoom
        });

        new window.google.maps.Marker({ position: center, map: map });
    }, [zoom, center])
    return (
        <div ref={mapRef}
             className={`map ${props.className}`}
             style={props.style}/>
    )
}

export default Map;
