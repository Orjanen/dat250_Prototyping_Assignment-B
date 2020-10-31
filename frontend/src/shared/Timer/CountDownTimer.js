import React, {useEffect, useState} from 'react';
import Countdown from 'react-countdown';


const CountDownTimer = (props) => {

    const [timeLeft, setTimeLeft] = useState(props.time)

    useEffect(() =>{
        const timer = setTimeout(() =>{
            setTimeLeft(timeLeft - 1000)
        }, 1000)

        return () =>{clearTimeout(timer)}
    }, [timeLeft])

    const Completionist = () => <span>The Vote is closed</span>;
    return (
        <Countdown
           date={1604238532286}
        >
            <Completionist/>
        </Countdown>
    );
}

export default CountDownTimer;