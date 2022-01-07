import '../../styles/category/Category.css';
import { useState } from 'react';

const Category = ({ id, name }) => {

    const [isEdit, setIsEdit] = useState(false);
    const [newCategory, setNewCategory] = useState(name);

    const handleOnClick = (e) => {
        e.preventDefault();
        const requestOptions = {
            method: 'DELETE',
            headers: { 'Content-Type': 'application/json', "Access-Control-Allow-Origin": "*" }
        };
        fetch(`http://localhost:8080/api/category/${id}`, requestOptions)
            .then(response => {
                if (response.status === 200) {
                    alert("Kategorie Usunieto!");
                    return response.json();
                }
                else {
                    alert("Błąd! Sprobuj raz jeszcze lub sprawdz, czy kategoria nie jest w uzyciu!!!");
                }

            })
            .then(data => {

                window.location.reload(false);
            })
            .catch(err => console.log(err));

    }

    const handleOnNewCategoryChange = (e) => setNewCategory(e.target.value);

    const handleOnEditSubmit = (e) => {
        e.preventDefault();

        if (newCategory === "") {
            alert("Wprowadz kategorie");
            return;
        }

        const category = { name: newCategory.trim() };

        const requestOptions = {
            method: 'PUT',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(category)
        };
        fetch(`http://localhost:8080/api/category/${id}`, requestOptions)
            .then(response => {
                if (response.status === 200) return response.json();

                if (response.status === 422) alert("Kategoria juz istnieje!");
            })
            .then(data => {

                setNewCategory("");
                window.location.reload(false);
            })
            .catch(err => console.log(err));



    }

    const handleOnEditClick = () => setIsEdit(p => !p);
    const editForm = (
        <form className="categoryForm" onSubmit={handleOnEditSubmit}>
            <label htmlFor="">
                <input type="text" value={newCategory} onChange={handleOnNewCategoryChange} />
            </label>
            <button onClick={handleOnEditSubmit}>Zatwierdź</button>
            <button onClick={handleOnEditClick}>Wróć</button>

        </form>
    )


    const categoryItem = <li className="categoryItem">{` name: ${name}`} <button onClick={handleOnEditClick}>Edytuj</button> <button onClick={handleOnClick}>Usun</button></li>


    return (
        <> {isEdit ? editForm : categoryItem}</>
    );
}

export default Category;
