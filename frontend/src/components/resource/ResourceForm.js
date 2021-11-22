import '../../styles/resource/ResourceForm.css';
import { useState, useEffect } from 'react';
import ResourceList from './ResourceList';

const ResourceForm = () => {

    const [categories, setCategories] = useState(null);
    const [resources, setResources] = useState(null);
    const [resource, setResource] = useState("");
    const [category, setCategory] = useState("-");


    useEffect(() => {
        fetch('http://localhost:8080/api/namespace')
            .then(response => response.json())
            .then(data => {
                setCategories(data);
            })
            .catch(err => console.log(err));

        fetch('http://localhost:8080/api/resources')
            .then(response => response.json())
            .then(data => {
                setResources(data);
            })
            .catch(err => console.log(err));


    }, [])

    const handleOnResourceChange = (e) => setResource(e.target.value);
    const handleOnCategoryChange = (e) => setCategory(e.target.value);

    const handleOnSubmit = (e) => {
        e.preventDefault();
        if (resource === "") {
            alert("Wprowadz czynnik");
            return;
        }
        if (category === "" || category === "-") {
            alert("Wprowadz kategorie");
            return;
        }
        const resorce = {
            name: resource,
            nameSpace: { name: category }
        };

        const requestOptions = {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(resorce)
        };
        fetch('http://localhost:8080/api/resource/add', requestOptions)
            .then(response => response.json())
            .then(data => {
                setCategory("");
                setResource("")
                window.location.reload(false);
            })
            .catch(err => console.log(err));



    }

    return (
        <div className="resourceWrapper">
            <h2>Formularz dodania czynnika</h2>
            <form className="resourceForm">
                <label onSubmit="">
                    <input type="text" value={resource} onChange={handleOnResourceChange} placeholder="mieso/cynk..." required />
                    <select value={category} onChange={handleOnCategoryChange} required>
                        <option>-</option>
                        {categories != null ? categories.map(c => <option>{c.name}</option>) : undefined}
                    </select>
                    <button onClick={handleOnSubmit}>Dodaj</button>
                </label>

            </form>

            <section className="availableResources">

                <h2>Dostepne czynniki</h2>

                {resources != null ? <ResourceList resources={resources} categories={categories} /> : undefined}
            </section>
        </div>

    );
}

export default ResourceForm;
