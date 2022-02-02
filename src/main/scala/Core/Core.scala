package Core

import chisel3._
import chisel3.util.experimental.loadMemoryFromFile
import Fetch.Fetch
import ALU.ALU
import ControlUnit.ControlUnit
import Decoder.Decoder
import RegFile.RegFile
import WriteBack.WriteBack

class Core extends Module
{
    // Declaring pins and modules
    val Fetch: Fetch = Module(new Fetch)
    val Decoder: Decoder = Module(new Decoder)
    val RegFile: RegFile = Module(new RegFile)
    val ALU: ALU = Module(new ALU)
    val ControlUnit: ControlUnit = Module(new ControlUnit)
    val WriteBack: WriteBack = Module(new WriteBack)
    val inst_memory: Mem[UInt] = Mem(1024, UInt(32.W))
    val ld_str_memory: Mem[SInt] = Mem(1024, SInt(32.W))
    val nPC: UInt = Fetch.io.PC_out + Decoder.io.imm.asUInt()
    loadMemoryFromFile(inst_memory, "assembly/hex_file.txt")
    
    // Wiring the modules
    when (ControlUnit.io.str_en)
    {
        ld_str_memory.write(ALU.io.out(23, 0), RegFile.io.rs2_data)
    }
    
    Array(  // Inputs
        // Decoder
        Decoder.io.in,
        
        // RegFile
        RegFile.io.rd_addr,
        RegFile.io.rs1_addr,
        RegFile.io.rs2_addr,
        RegFile.io.rd_data,
        
        // ALU
        ALU.io.func3,
        ALU.io.func7,
        ALU.io.imm,
        ALU.io.id,
        ALU.io.rs1,
        ALU.io.rs2,
        ALU.io.op2sel,
        
        // Fetch
        Fetch.io.nPC_en,
        Fetch.io.nPC_in,
        
        // Control Unit
        ControlUnit.io.id,
        
        // WriteBack
        WriteBack.io.alu_in,
        WriteBack.io.nPC,
        WriteBack.io.nPC_en,
        WriteBack.io.load_in,
        WriteBack.io.ld_en
        
        // ld_str_memory
    ) zip Array(  // Corresponding input wires
        // Decoder
        inst_memory.read(Fetch.io.inst_out),
        
        // RegFile
        Decoder.io.rd,
        Decoder.io.rs1,
        Decoder.io.rs2,
        WriteBack.io.out,
        
        // ALU
        Decoder.io.func3,
        Decoder.io.func7,
        Decoder.io.imm,
        Decoder.io.id,
        RegFile.io.rs1_data,
        RegFile.io.rs2_data,
        ControlUnit.io.op2sel,
        
        // Fetch
        ControlUnit.io.jal,
        nPC,
        
        // Control Unit
        Decoder.io.id,
        
        // WriteBack
        ALU.io.out,
        Fetch.io.PC_out,
        ControlUnit.io.jal,
        ld_str_memory.read(ALU.io.out(23, 0)),
        ControlUnit.io.ld_en
    ) foreach
    {
        x => x._1 := x._2
    }
}
