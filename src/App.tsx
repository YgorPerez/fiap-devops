import './App.css';
import { Button } from './components/button';
import Navbar from './components/navbar/navbar';

function App() {
  return (
    <>
      <div className='h-screen bg-slate-200'>
        <Navbar />
        <div>
          <Button className='' size={'lg'} variant={'outline'}>
            <div className='flex flex-col'>
              <div>Clique</div>
              <div>Aqui</div>
            </div>
          </Button>
        </div>
      </div>
    </>
  );
}

export default App;
