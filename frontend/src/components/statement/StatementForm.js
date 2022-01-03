import '../../styles/statement/StatementForm.css';
import { useEffect, useState } from 'react';

const StatementForm = () => {
    const [currentModels, setCurrentModels] = useState(null);
    const [currentResources, setCurrentResources] = useState(null);
    const [currentSources, setCurrentSources] = useState(null);
    const [currentVerbs, setCurrentVerbs] = useState(null);

    useEffect(() => {
        fetch('http://localhost:8080/api/models')
            .then(response => response.json())
            .then(data => {
                setCurrentModels(data);
            })
            .catch(err => console.log(err));

        fetch('http://localhost:8080/api/resources')
            .then(response => response.json())
            .then(data => {
                setCurrentResources(data);
            })
            .catch(err => console.log(err));

        fetch('http://localhost:8080/api/sources')
            .then(response => response.json())
            .then(data => {
                let tmp = data.filter(s => s.name !== "Automatically prediction");

                setCurrentSources(tmp);
            })
            .catch(err => console.log(err));

        fetch('http://localhost:8080/api/verbs')
            .then(response => response.json())
            .then(data => {
                setCurrentVerbs(data);
            })
            .catch(err => console.log(err));


    }, []);

    const [modelName, setModelName] = useState(sessionStorage.getItem('modelName') ? sessionStorage.getItem('modelName') : "");
    const [subject, setSubject] = useState(sessionStorage.getItem('subject') ? sessionStorage.getItem('subject') : "-");
    const [subjectCategory, setSubjectCategory] = useState(sessionStorage.getItem('subjectCategory') ? sessionStorage.getItem('subjectCategory') : "");
    const [predicate, setPredicate] = useState(sessionStorage.getItem('predicate') ? sessionStorage.getItem('predicate') : "-");
    const [resource, setResource] = useState(sessionStorage.getItem('resource') ? sessionStorage.getItem('resource') : "-");
    const [source, setSource] = useState(sessionStorage.getItem('source') ? sessionStorage.getItem('source') : "-");
    const [resourceCategory, setResourceCategory] = useState(sessionStorage.getItem('resourceCategory') ? sessionStorage.getItem('resourceCategory') : "");
    const [literal, setLiteral] = useState("");
    const [probability, setProbability] = useState();
    const [properties, setProperties] = useState([{ key: "", value: "" }])

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

    const handleOnModelNameChange = (e) => {
        setModelName(e.target.value);
        sessionStorage.setItem('modelName', e.target.value);
    }

    const handleOnSourceChange = (e) => {
        setSource(e.target.value);
        sessionStorage.setItem('source', e.target.value);
    }

    const handleOnSubjectChange = (e) => {
        setSubject(e.target.value);
        sessionStorage.setItem('subject', e.target.value);

        let r = currentResources.find(r => r.name === e.target.value);
        if (r && r.namespace != null) {
            setSubjectCategory(r.namespace.name);
            sessionStorage.setItem('subjectCategory', r.namespace.name);
        }
    }
    const handleOnPredicateChange = (e) => {
        setPredicate(e.target.value);
        sessionStorage.setItem('predicate', e.target.value);
    }
    const handleOnResourceChange = (e) => {
        setResource(e.target.value);
        sessionStorage.setItem('resource', e.target.value);

        let r = currentResources.find(r => r.name === e.target.value);
        if (r && r.namespace != null) {
            setResourceCategory(r.namespace.name);
            sessionStorage.setItem('resourceCategory', r.namespace.name);
        }
    }
    const handleOnLiteralChange = (e) => {
        setLiteral(e.target.value);
    }
    const handleOnProbabilityChange = (e) => {
        setProbability(e.target.value);
    }
    const handleOnSubjectCategoryChange = (e) => {
        setSubjectCategory(e.target.value);
    }
    const handleOnResourceCategoryChange = (e) => {
        setResourceCategory(e.target.value);
    }

    const handleOnClick = (e) => {
        e.preventDefault();

        if (source === "" || source === "-") {
            alert("Wybierz źródło");
            return;
        }

        if (modelName === "" || modelName === "-") {
            alert("Wprowadz nazwe modelu");
            return;
        }
        if (subject === "" || subject === "-") {
            alert("Wprowadz subject");
            return;
        }

        // if (subjectCategory === "") {
        //     alert("Wprowadz subject category");
        //     return;
        // }

        if (predicate === "" || predicate === "-") {
            alert("Wprowadz predicate");
            return;
        }

        if ((resource === "" || resource === "-") && literal === "") {
            alert('wprowadz literal lub resorce');
            return;
        }
        if ((resource !== "" && resource !== "-") && literal !== "") {
            alert('wprowadz tylko jedno');
            return;
        }
        // if (resource !== "" && resourceCategory === "") {
        //     alert('wprowadz resource category');
        //     return;
        // }
        if (probability === undefined) {
            alert("Wprowadz probability");
            return;
        }
        if (probability < -1 || probability > 1) {
            alert("Błędne probability");
            return;
        }


        const statement = {
            model: { name: modelName },
            properties: properties,
            subject: {
                name: subject,
                nameSpace: { name: subjectCategory }
            },
            source: {
                name: source.split(' |: ')[0].trim().replace('|:', '').trim()
            },
            predicate: { verb: predicate },
            probability: probability
        }
        if (literal !== "") {
            statement.literal = {
                value: literal.trim(),
                dataType: "string"
            }
        }
        if (resource !== "" && resource !== "-") {
            statement.resource = {
                name: resource,
                nameSpace: { name: resourceCategory }
            }
        }
        //console.log('statement ' + JSON.stringify(statement));
        // console.log(JSON.stringify(statement));

        const requestOptions = {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(statement)
        };
        fetch('http://localhost:8080/api/statement/add', requestOptions)
            .then(response => response.json())
            .then(data => {
                console.log(data);
                window.location.reload(false);
            })
            .catch(err => console.log(err));



    }




    return (
        <form className="statementForm">
            <label htmlFor="source">Źródło
            </label>
            <select id="source" value={source} onChange={handleOnSourceChange} required>
                <option>-</option>
                {currentSources != null ? currentSources.map(r => {
                    let option2Display = `${r.name} |: `;

                    for (const idx in r.properties) {
                        option2Display += ` ${r.properties[idx].key}: ${r.properties[idx].value} `;

                    }
                    return <option >{option2Display}  </option>
                }) : undefined}
            </select>

            <label htmlFor="m_name">Nazwa zbioru


                {/* <input id="m_name" type="text" placeholder="model" value={modelName} onChange={handleOnModelNameChange} /> */}
            </label>
            <select id="m_name" value={modelName} onChange={handleOnModelNameChange} required>
                <option>-</option>
                {currentModels != null ? currentModels.map(m => <option>{m.name}</option>) : undefined}
            </select>

            <label htmlFor="subject">Czynnik
                {/* <input id="subject" type="text" placeholder="subject" value={subject} onChange={handleOnSubjectChange} /> */}

            </label>
            <select id="subject" value={subject} onChange={handleOnSubjectChange} required>
                <option>-</option>
                {currentResources != null ? currentResources.map(r => <option>{r.name}</option>) : undefined}
            </select>
            <label htmlFor="subjectCategory">kategoria

                {/* <input id="subjectCategory" type="text" placeholder="subjectCategory" value={subjectCategory} onChange={handleOnSubjectCategoryChange} /> */}
            </label>
            <input id="subjectCategory" type="text" placeholder="subjectCategory" value={subjectCategory} onChange={handleOnSubjectCategoryChange} disabled />

            <label htmlFor="predicate">zależność

                {/* <input id="predicate" type="text" placeholder="predicate" value={predicate} onChange={handleOnPredicateChange} /> */}
            </label>
            <select id="predicate" value={predicate} onChange={handleOnPredicateChange} required>
                <option>-</option>
                {currentVerbs != null ? currentVerbs.map(v => <option>{v.verb}</option>) : undefined}
            </select>
            <label htmlFor="resource">obiekt
                {/* <input id="resource" type="text" placeholder="resource" value={resource} onChange={handleOnResourceChange} /> */}

            </label>
            <select id="resource" value={resource} onChange={handleOnResourceChange} required>
                <option>-</option>
                {currentResources != null ? currentResources.map(r => <option>{r.name}</option>) : undefined}
            </select>
            <label htmlFor="resourceCategory">kategoria
                {/* <input id="resourceCategory" type="text" placeholder="resourceCategory" value={resourceCategory} onChange={handleOnResourceCategoryChange} /> */}
            </label>
            <input id="resourceCategory" type="text" placeholder="resourceCategory" value={resourceCategory} onChange={handleOnResourceCategoryChange} disabled />

            <label htmlFor="literal">literał
            </label>
            <input id="literal" type="text" placeholder="literal" value={literal} onChange={handleOnLiteralChange} />

            <label htmlFor="probability">wiarygodność
            </label>
            <input id="probability" type="number" step="0.01" min="-1" max="1" placeholder="probability" value={probability} onChange={handleOnProbabilityChange} />


            {properties.map((element, index) => (
                <div className="form-inline" key={index}>
                    <div className="keyWrapper">
                        <label>Klucz</label>
                        <select name="key" value={element.key || ""} onChange={e => handleChange(index, e)} required>
                            <option>-</option>
                            <option>Autor</option>
                            <option>Data</option>

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
            <button onClick={handleOnClick}>Dodaj</button>

        </form>
    );
}

export default StatementForm;
