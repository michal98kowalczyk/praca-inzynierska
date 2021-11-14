import '../styles/Model.css';
import { Link } from "react-router-dom";

const Model = ({ id, name, statements }) => {

    return (
        <li>{name} <Link to={`/model/${id}/${name}`} >Odkryj</Link></li>
    );
}

export default Model;
