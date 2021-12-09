import '../../styles/source/SourceForm.css';
import { useState, useEffect } from 'react';

import SourceList from './SourceList';

const SOURCE_NAME = "Źródło";

const SourceForm = () => {

    const [sources, setSources] = useState(null);
    const [source, setSource] = useState("");
    const [properties, setProperties] = useState([{ key: "", value: "" }])


    useEffect(() => {


        fetch('http://localhost:8080/api/sources')
            .then(response => response.json())
            .then(data => {
                setSources(data);
            })
            .catch(err => console.log(err));


    }, [])

    const handleOnSourceChange = (e) => setSource(e.target.value);

    let handleChange = (i, e) => {
        let newFormValues = [...properties];
        newFormValues[i][e.target.name] = e.target.value;
        setProperties(newFormValues);
    }

    let addFormFields = () => {
        setProperties([...properties, { key: "", value: "" }])
    }

    let removeFormFields = (i) => {
        let newFormValues = [...properties];
        newFormValues.splice(i, 1);
        setProperties(newFormValues)
    }

    const handleOnSubmit = (e) => {
        e.preventDefault();
        if (source === "") {
            alert("Wprowadz źródło");
            return;
        }

        const newSource = {
            name: source,
            nameSpace: { name: SOURCE_NAME },
            properties: properties
        };
        const requestOptions = {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(newSource)
        };
        fetch('http://localhost:8080/api/resource/add', requestOptions)
            .then(response => response.json())
            .then(data => {
                setSource("")
                setProperties([{ key: "", value: "" }]);
                window.location.reload(false);
            })
            .catch(err => console.log(err));



    }

    return (
        <div className="SourceWrapper">
            <h2>Formularz dodania źródła</h2>
            <form className="sourceForm">
                <label onSubmit="">
                    <input type="text" value={source} onChange={handleOnSourceChange} placeholder="url..." required />

                </label>

                {properties.map((element, index) => (
                    <div className="form-inline" key={index}>
                        <div className="keyWrapper">
                            <label>Klucz</label>
                            <select name="key" value={element.key || ""} onChange={e => handleChange(index, e)} required>
                                <option>-</option>
                                <option>Autor</option>
                                <option>Źródło</option>
                                <option>Data</option>
                                <option>Wydawca</option>
                            </select>
                        </div>
                        {/* <input type="text" name="key" value={element.key || ""} onChange={e => handleChange(index, e)} /> */}
                        <div className="valueWrapper">
                            <label>Wartość</label>
                            {element.key === "Data" ? <input type="date" name="value" value={element.value || ""} onChange={e => handleChange(index, e)} /> : <input type="text" name="value" value={element.value || ""} onChange={e => handleChange(index, e)} />}
                        </div>
                        {
                            index ?
                                <button type="button" className="button remove" onClick={() => removeFormFields(index)}>Remove</button>
                                : null
                        }

                    </div>
                ))}
                <button className="button add" type="button" onClick={() => addFormFields()}>Dodaj parametr</button>

                <button onClick={handleOnSubmit}>Dodaj</button>
            </form>

            <section className="availableSources">

                <h2>Dostepne źródła</h2>

                {sources != null ? <SourceList sources={sources.filter(s => s.name !== "Automatically prediction")} /> : undefined}
            </section>
        </div>

    );
}

export default SourceForm;
