import Link from './link';

const Navbar = () => {
  return (
    <header>
      <nav className='flex h-32 items-center justify-between bg-white px-80'>
        <Link>home</Link>
        <Link>About</Link>
        <Link>Teste</Link>
        <Link>Site</Link>
      </nav>
    </header>
  );
};

export default Navbar;
