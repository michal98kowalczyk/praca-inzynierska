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
import SourceForm from './source/SourceForm'
import Footer from './Footer'

const App = () => {

  return (
    <div className="App">
      <Header />
      <div className="wrapper">


        <Routes>

          <Route path='/' element={<Main />} />

          <Route path="/model/:id/:name" element={<ModelDetails />} />
          <Route path="/model/form" element={<ModelForm />} />
          <Route path="/statement/form" element={<StatementForm />} />
          <Route path="/source/form" element={<SourceForm />} />
          <Route path="/category/form" element={<CategoryForm />} />
          <Route path="/resource/form" element={<ResourceForm />} />
          <Route path="/verb/form" element={<VerbForm />} />
          <Route path='*' element={<PageNotFound />} />

        </Routes>


      </div>
      <Footer />
    </div>
  );
}

export default App;
