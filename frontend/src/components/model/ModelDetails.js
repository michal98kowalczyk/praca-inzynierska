import '../../styles/model/ModelDetails.css';
import { useEffect, useState } from 'react'
import { useParams } from "react-router-dom";
import StatementList from '../statement/StatementList';

const ModelDetails = (props) => {
    const { id, name } = useParams();
    const [statements, setStatements] = useState(null);


    useEffect(() => {
        fetch(`http://localhost:8080/api/model/${id}/statements`)
            .then(response => response.json())
            .then(data => {
                setStatements(data);
            })
            .catch(err => console.log(err));
    }, []);
    return (

        <div>
            <h2> {name} </h2>
            {statements !== null ? <StatementList statements={statements} /> : undefined}

        </div>


    );
}

export default ModelDetails;
