import '../../styles/verb/VerbForm.css';
import { useState, useEffect } from 'react';
import VerbList from './VerbList';

const VerbForm = () => {

    const [verbs, setVerbs] = useState(null);
    const [newVerb, setNewVerb] = useState("");


    useEffect(() => {
        console.log('czas pobrania verbs ');
        console.time("timer");
        fetch('http://localhost:8080/api/verbs')
            .then(response => response.json())
            .then(data => {
                setVerbs(data);
            })
            .catch(err => console.log(err));
        console.timeEnd("timer");

    }, [])

    const handleOnVerbChange = (e) => setNewVerb(e.target.value);

    const handleOnSubmit = (e) => {
        e.preventDefault();
        if (newVerb === "") {
            alert("Wprowadz relacje");
            return;
        }

        const verb = {
            verb: newVerb
        };

        const requestOptions = {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(verb)
        };
        fetch('http://localhost:8080/api/verb/add', requestOptions)
            .then(response => {
                if (response.status === 200) return response.json();

                if (response.status === 422) alert("Relacja juz istnieje!");
            })
            .then(data => {

                setNewVerb("");
                window.location.reload(false);
            })
            .catch(err => console.log(err));



    }

    return (
        <div className="verbWrapper">
            <h2>Formularz dodania relacji</h2>
            <form className="verbForm">
                <label onSubmit={handleOnSubmit}>
                    <input type="text" value={newVerb} onChange={handleOnVerbChange} placeholder="szkodzi..." required />

                    <button onClick={handleOnSubmit}>Dodaj</button>
                </label>

            </form>

            <section className="availableVerbs">
                <h2>Dostepne relacje</h2>

                {verbs != null ? <VerbList verbs={verbs} /> : undefined}

            </section>
        </div>

    );
}

export default VerbForm;
