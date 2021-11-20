import '../styles/Header.css';
import { Link } from "react-router-dom";

const Header = () => {
    return (
        <header>
            <h1>Ontology App</h1>
            <nav><Link to="/" >Strona główna</ Link >
                <Link to="/model/form" >Dodaj model</ Link >
                <Link to="/statement/form" >Dodaj statement</ Link >
                <Link to="/category/form" >Dodaj kategorie</ Link >
            </nav>

        </header>
    );
}

export default Header;
