import Header from './Header'
import Main from './Main'
import ModelDetails from './model/ModelDetails'
import ModelForm from './model/ModelForm'
import StatementForm from './statement/StatementForm'
import PageNotFound from './PageNotFound'
import { Routes, Route } from "react-router-dom";

import '../styles/App.css';
import CategoryForm from './category/CategoryForm'
import ResourceForm from './resource/ResourceForm'
import VerbForm from './verb/VerbForm'

const App = () => {

  return (
    <div className="App">
      <Header />

      <Routes>

        <Route path='/' element={<Main />} />

        <Route path="/model/:id/:name" element={<ModelDetails />} />
        <Route path="/model/form" element={<ModelForm />} />
        <Route path="/statement/form" element={<StatementForm />} />
        <Route path="/category/form" element={<CategoryForm />} />
        <Route path="/resource/form" element={<ResourceForm />} />
        <Route path="/verb/form" element={<VerbForm />} />
        <Route path='*' element={<PageNotFound />} />

      </Routes>
    </div>
  );
}

export default App;
