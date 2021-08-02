import kotlin.math.*

import org.bytedeco.javacpp.*
import org.bytedeco.javacpp.indexer.*
import org.bytedeco.mkl.global.mkl_rt.*
import kotlin.system.exitProcess


fun main(args: Array<String>) {
    println(
        "This example computes the real matrix C=alpha*A*B + beta*C using\n"
        + "the Intel(R) MKL function dgemm, where A, B, and C are matrices and\n"
        + "alpha and beta are double precision scalars.\n"
    )

    val m = 2000L
    val p = 200L
    val n = 1000L

    println(
        "Initializing data matrix multiplication C=A*B for matrix\n"
        + "A ($m by $p) and matrix B ($p by $n).\n"
    )

    val alpha = 1.0
    val beta = 0.0
    println("Allocating memory for matrices aligned on 64-byte boundary for better performance.\n")
    val A = DoublePointer(MKL_malloc(m * p * java.lang.Double.BYTES, 64))
    val B = DoublePointer(MKL_malloc(p * n * java.lang.Double.BYTES, 64))
    val C = DoublePointer(MKL_malloc(m * n * java.lang.Double.BYTES, 64))
    if (A.isNull || B.isNull || C.isNull) {
        println("\n ERROR: Can't allocate memory for matrices. Aborting... \n")
        MKL_free(A)
        MKL_free(B)
        MKL_free(C)
        exitProcess(1)
    }
    println("Initializing matrix data.\n")
    val Aidx: DoubleIndexer = DoubleIndexer.create(A.capacity(m * p))
    for (i in 0 until m * p) {
        A.put(i, (i + 1).toDouble())
    }
    val Bidx: DoubleIndexer = DoubleIndexer.create(B.capacity(p * n))
    for (i in 0 until p * n) {
        B.put(i, (-i - 1).toDouble())
    }
    val Cidx: DoubleIndexer = DoubleIndexer.create(C.capacity(m * n))
    for (i in 0 until m * n) {
        C.put(i, 0.0)
    }
    println("Computing matrix product using Intel(R) MKL dgemm function via CBLAS interface.\n")
    cblas_dgemm(
        CblasRowMajor, CblasNoTrans, CblasNoTrans,
        m.toInt(), n.toInt(), p.toInt(), alpha, A, p.toInt(), B, n.toInt(), beta, C, n.toInt()
    )
    println("Computations completed.\n")

    println("Top left corner of matrix A: ")
    for (i in 0 until min(m, 6)) {
        for (j in 0 until min(p, 6)) {
            System.out.printf("%12.0f", Aidx.get(j + i * p))
        }
        println()
    }
    println()

    println("Top left corner of matrix B: ")
    for (i in 0 until min(p, 6)) {
        for (j in 0 until min(n, 6)) {
            System.out.printf("%12.0f", Bidx.get(j + i * n))
        }
        println()
    }
    println()

    println("Top left corner of matrix C: ")
    for (i in 0 until min(m, 6)) {
        for (j in 0 until min(n, 6)) {
            System.out.printf("%12.5G", Cidx.get(j + i * n))
        }
        println()
    }
    println()

    println("Deallocating memory.\n")
    MKL_free(A)
    MKL_free(B)
    MKL_free(C)
    println("Example completed.\n")
    exitProcess(0)
}
