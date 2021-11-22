import '../../styles/model/Model.css';
import { Link } from "react-router-dom";
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome'
import { faTrash } from '@fortawesome/free-solid-svg-icons'

const Model = ({ id, name, statements }) => {

    const handleOnClick = (e) => {
        e.preventDefault();

        const requestOptions = {
            method: 'DELETE',
            headers: { 'Content-Type': 'application/json', "Access-Control-Allow-Origin": "*" }
        };
        fetch(`http://localhost:8080/api/model/${id}`, requestOptions)
            .then(response => {
                if (response.status === 200) return response.json();

            })
            .then(data => {
                alert("Model Usunieto!");
                window.location.reload(false);
            })
            .catch(err => console.log(err));

    }

    return (
        <li> <Link to={`/model/${id}/${name}`} >{name}</Link> <button onClick={handleOnClick}><FontAwesomeIcon icon={faTrash} /></button></li>
    );
}

export default Model;
