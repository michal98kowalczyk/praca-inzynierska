import '../../styles/model/ModelDetails.css';
import { useEffect, useState } from 'react'
import { useParams } from "react-router-dom";
import StatementList from '../statement/StatementList';

const ModelDetails = () => {
    const { id, name } = useParams();
    const [statements, setStatements] = useState(null);
    const [isFiltered, setIsFiltered] = useState(false);
    const [searchingSubject, setSearchingSubject] = useState('');
    const [searchingPredicate, setSearchingPredicate] = useState('');
    const [searchingResource, setSearchingResource] = useState('');
    const [currentResources, setCurrentResources] = useState(null);
    const [currentVerbs, setCurrentVerbs] = useState(null);

    useEffect(() => {
        fetch(`http://localhost:8080/api/model/${id}/statements`)
            .then(response => response.json())
            .then(data => {
                setStatements(data);
            })
            .catch(err => console.log(err));

        fetch('http://localhost:8080/api/resources')
            .then(response => response.json())
            .then(data => {
                setCurrentResources(data);
            })
            .catch(err => console.log(err));

        fetch('http://localhost:8080/api/verbs')
            .then(response => response.json())
            .then(data => {
                setCurrentVerbs(data);
            })
            .catch(err => console.log(err));
    }, []);

    const applyFilter = (e) => {
        e.preventDefault();
        setIsFiltered(true);
    }
    const clearFilter = (e) => {
        e.preventDefault();
        setIsFiltered(false);
        setSearchingSubject('-');
        setSearchingPredicate('-');
        setSearchingResource('-');
    }

    const handleOnSubjectChange = (e) => {
        setSearchingSubject(e.target.value);

    }
    const handleOnPredicateChange = (e) => {
        setSearchingPredicate(e.target.value);

    }
    const handleOnResourceChange = (e) => {
        setSearchingResource(e.target.value);

    }

    const getStatements2Display = () => {
        if (isFiltered === false || ((searchingSubject === '' || searchingSubject === '-') && (searchingPredicate === '' || searchingPredicate === '-') && (searchingResource === '' || searchingResource === '-'))) {
            return statements;
        }

        let statements2display;
        if (searchingSubject !== '' && searchingSubject !== '-') {
            statements2display = statements.filter(s => s.subject.name === searchingSubject);
        } else {
            statements2display = statements;
        }
        if (searchingPredicate !== '' && searchingPredicate !== '-') {
            statements2display = statements2display.filter(s => s.predicate.verb === searchingPredicate);

        }
        if (searchingResource !== '' && searchingResource !== '-') {
            statements2display = statements2display.filter(s => s.resource.name === searchingResource);

        }

        return statements2display;

    }

    return (

        <div>
            <form className='statementEditForm'>
                <label htmlFor="searchingSubject">Czynnik

                </label>
                <select id="searchingSubject" value={searchingSubject} onChange={handleOnSubjectChange} required>
                    <option>-</option>
                    {currentResources != null ? currentResources.map(r => <option>{r.name}</option>) : undefined}
                </select>
                <label htmlFor="searchingPredicate">zależność
                </label>
                <select id="searchingPredicate" value={searchingPredicate} onChange={handleOnPredicateChange} required>
                    <option>-</option>
                    {currentVerbs != null ? currentVerbs.map(v => <option>{v.verb}</option>) : undefined}
                </select>

                <label htmlFor="searchingResource">obiekt
                </label>
                <select id="searchingResource" value={searchingResource} onChange={handleOnResourceChange} required>
                    <option>-</option>
                    {currentResources != null ? currentResources.map(r => <option>{r.name}</option>) : undefined}
                </select>

                <button onClick={applyFilter}>Filtruj</button>
                <button onClick={clearFilter}>Wyczyść</button>
            </form>



            <h2> {name} </h2>
            {statements !== null ? <StatementList statements={getStatements2Display()} /> : <h2>Brak wyników</h2>}

        </div>


    );
}

export default ModelDetails;
