import '../../styles/statement/StatementItemDetails.css';
import { useParams } from "react-router-dom";
import { useEffect, useState } from 'react';

import { FontAwesomeIcon } from '@fortawesome/react-fontawesome'
import { faTimesCircle } from '@fortawesome/free-solid-svg-icons'

const StatementItemDetails = ({ statement, closeDetails }) => {

    const [detailsWrapper, setDetailsWrapper] = useState(null);

    const { name } = useParams();

    const properties = statement.properties.map(property => <><span>{property.key} </span>{property.value} </>)
    const source = statement.source;
    const sourceProperties = source.properties.map(property => <><span>{property.key} </span>{property.value} </>)

    useEffect(() => {

        fetch(`http://localhost:8080/api/statement/${statement.id}/details`)
            .then(response => response.json())
            .then(data => {
                setDetailsWrapper(data);
            })
            .catch(err => console.log(err));




    }, []);

    const handleOnDetailsClick = () => closeDetails(false);

    return (
        <div className='statementDetailsWrapper'>

            <section className='statementItemDetails'>
                <div onClick={handleOnDetailsClick} className='closeButton'><FontAwesomeIcon icon={faTimesCircle} /></div>
                <p><span>Zbiór</span> {name} <br />

                </p>
                <p><span>Źródło</span> {statement.source.name} <br />
                    {sourceProperties}
                    <br />
                </p>
                <p><span>Czynnik</span> {statement.subject.name} </p>
                <p><span>Relacja</span> {statement.predicate.verb} </p>


                {statement.isRes && <p><span>Obiekt</span> {statement.resource.name} </p>}
                {statement.isLit && <p><span>Literał</span> {statement.literal.value} </p>}
                <p><span>Wiarygodność</span> {statement.confidence}</p> <br />
                <p>{properties}</p>

                <section className='statementStats'>
                    <p><span>Łączna ilość predykcji:</span> {detailsWrapper !== null ? detailsWrapper.countOfPrediction : undefined}</p>
                    <p><span>Predykcje potwierdzające:</span> {detailsWrapper !== null ? detailsWrapper.countOfPositivePrediction : undefined}</p>
                    <p><span>Predykcje sprzeczne:</span> {detailsWrapper !== null ? detailsWrapper.countOfNegativePrediction : undefined}</p>
                    <p><span>Wiarygodność minimalna:</span> {detailsWrapper !== null ? detailsWrapper.minConfidence : undefined}</p>
                    <p><span>Wiarygodność maksymalna:</span> {detailsWrapper !== null ? detailsWrapper.maxConfidence : undefined}</p>
                    <p><span>Wiarygodność średnia:</span> {detailsWrapper !== null ? detailsWrapper.avgConfidence : undefined}</p>
                </section>

            </section>



        </div>
    );
}

export default StatementItemDetails;
