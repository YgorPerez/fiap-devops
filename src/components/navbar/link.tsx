import { Button } from '../button';

type Props = {
  children: React.ReactNode;
};

const Link = ({ children }: Props) => {
  return (
    <Button asChild variant={'outline'} size={'lg'}>
      <a
        href=''
        className='font-semibold transition-all duration-200 hover:text-blue-400'>
        {children}
      </a>
    </Button>
  );
};

export default Link;
