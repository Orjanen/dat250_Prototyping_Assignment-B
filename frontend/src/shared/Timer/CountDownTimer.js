import React, {useEffect, useState} from 'react';
import Countdown from 'react-countdown';


const CountDownTimer = (props) => {

    const [time, setTimeGonePast] = useState(0)

    const removePT = props.time && props.time.split('PT')[1]
    const hourSplit = removePT && removePT.includes('H') ? (removePT.split('H')) : null
    let minutesSplit = 0;
    if (hourSplit){
        minutesSplit = hourSplit[1].split('M')[0]
    } else {
        minutesSplit = removePT && removePT.includes('M') ? (removePT.split('M')) : null
    }
    let secSplit = 0;
    if (minutesSplit){
        secSplit = minutesSplit[1].split('.')[0]
    } else {
        secSplit = removePT && removePT.includes('.') ? removePT.split('.')[0] : null
    }

    let duration ={
        hours: hourSplit ? +hourSplit[0] : 0,
        minutes: minutesSplit ?  +minutesSplit[0] : 0,
        seconds:  secSplit ? +secSplit : 0
    }

    let milTime  = (duration.hours * (60000 * 60) + duration.minutes * 60000 + duration.seconds * 1000)
    let timeGonePast = 0



    useEffect(() =>{
        const timer =setInterval(() =>{
            timeGonePast += 1000
            setTimeGonePast(timeGonePast)
        },1000)
        return () => clearTimeout(timer)

    },[timeGonePast])

    const Completionist = () => <span>The Vote is closed</span>;
    return (
        <Countdown
           date={Date.now() + (milTime - time)}
        >
            <Completionist/>
        </Countdown>
    );
}

export default CountDownTimer;