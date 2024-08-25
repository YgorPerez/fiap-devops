interface TestProps {
  children?: React.ReactNode
  className?: string
}

const Test = ({children, className}: TestProps) => {
  return (
    <button className={`text-3xl rounded-xl text-red-500 bg-green-600 ${className}`}>
      Teste {children}
    </button>
  )
}

export default Test
