import '../styles/Header.css';
import { Link } from "react-router-dom";

const Header = () => {
    return (
        <header>
            <h1>Ontology App</h1>
            <nav>
                <button>
                    <Link to="/" >Strona główna</ Link ></button>
                <button>
                    <Link to="/source/form" >Dodaj źródło</ Link ></button>
                <button>
                    <Link to="/model/form" >Dodaj model</ Link ></button>
                <button>
                    <Link to="/statement/form" >Dodaj statement</ Link ></button>
                <button>
                    <Link to="/category/form" >Dodaj kategorie</ Link ></button>
                <button>
                    <Link to="/resource/form" >Dodaj czynnik</ Link ></button>
                <button>
                    <Link to="/verb/form" >Dodaj relacje</ Link ></button>
            </nav>

        </header>
    );
}

export default Header;
