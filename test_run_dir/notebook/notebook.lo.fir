circuit notebook :
  module notebook :
    input clock : Clock
    input reset : UInt<1>
    input io_in : UInt<4>
    output io_out : UInt<4>

    node _io_out_T = cat(io_in, UInt<1>("h0")) @[Cat.scala 30:58]
    io_out <= bits(_io_out_T, 3, 0) @[notebook.scala 13:12]
