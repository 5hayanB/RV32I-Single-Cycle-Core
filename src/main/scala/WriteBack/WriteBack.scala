package WriteBack

import chisel3._

class WriteBack_IO extends Bundle
{
    val alu_in: SInt = Input(SInt(32.W))
    val nPC: UInt = Input(UInt(32.W))
    val nPC_en: Bool = Input(Bool())
    val load_in: SInt = Input(SInt(32.W))
    val ld_en: Bool = Input(Bool())
//    val auipc_in: SInt = Input(SInt(32.W))
//    val lui_in: SInt = Input(SInt(32.W))
    val out: SInt = Output(SInt(32.W))
}
class WriteBack extends Module
{
    // Initializing the signals and modules
    val io: WriteBack_IO = IO(new WriteBack_IO)
    val alu_in: SInt = dontTouch(WireInit(io.alu_in))
    val nPC: UInt = dontTouch(WireInit(io.nPC))
    val nPC_en: Bool = dontTouch(WireInit(io.nPC_en))
    
    // Wiring the output
    io.out := Mux(
        nPC_en, (nPC + 4.U).asSInt(), Mux(
            io.ld_en, io.load_in, io.alu_in
        )
    )
}
