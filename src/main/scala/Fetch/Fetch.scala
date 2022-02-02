package Fetch

import chisel3._
import chisel3.util._

class Fetch_IO extends Bundle
{
    val nPC_in: UInt = Input(UInt(32.W))
    val nPC_en: Bool = Input(Bool())
    val inst_out: UInt = Output(UInt(24.W))
    val PC_out: UInt = Output(UInt(32.W))
}
class Fetch extends Module
{
    // Initializing the signals and modules
    val io: Fetch_IO = IO(new Fetch_IO)
    val PC: UInt = dontTouch(RegInit(0.U(32.W)))
    val nPC_in: UInt = dontTouch(WireInit(io.nPC_in))
    val nPC_en: Bool = dontTouch(WireInit(io.nPC_en))
    val inst_out: UInt = dontTouch(WireInit(Cat(PC(25, 2))))
    val PC_out: UInt = dontTouch(WireInit(PC))
    
    // Wiring the outputs
    io.inst_out := inst_out
    io.PC_out := PC_out
    
    // Next instruction address
    PC := Mux(
        io.nPC_en,
        io.nPC_in,
        PC + 4.U
    )
}
