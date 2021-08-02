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

    val m = 3
    val p = 3
    val n = 3

    println(
        "Initializing data matrix multiplication C=A*B for matrix\n"
        + "A ($m by $p) and matrix B ($p by $n).\n"
    )

    val alpha = 1.0
    val beta = 0.0

    val A = doubleArrayOf(
        0.7220180,  0.07121225, 0.6881997,
        -0.2648886, -0.89044952, 0.3700456,
        -0.6391588,  0.44947578, 0.6240573,
    )
    val B = doubleArrayOf(
        0.6881997, -0.07121225,  0.7220180,
        0.3700456,  0.89044952, -0.2648886,
        0.6240573, -0.44947578, -0.6391588,
    )
    val C = doubleArrayOf(
        0.0, 0.0, 0.0,
        0.0, 0.0, 0.0,
        0.0, 0.0, 0.0,
    )

    println("Computing matrix product using Intel(R) MKL dgemm function via CBLAS interface.\n")
    cblas_dgemm(
        CblasRowMajor, CblasNoTrans, CblasNoTrans, m, n, p, alpha, A, p, B, n, beta, C, n
    )
    println("Computations completed.\n")

    println("Top left corner of matrix A: ")
    for (i in 0 until min(m, 6)) {
        for (j in 0 until min(p, 6)) {
            System.out.printf("%12.5G", A[j + i * p])
        }
        println()
    }
    println()

    println("Top left corner of matrix B: ")
    for (i in 0 until min(p, 6)) {
        for (j in 0 until min(n, 6)) {
            System.out.printf("%12.5G", B[j + i * n])
        }
        println()
    }
    println()

    println("Top left corner of matrix C: ")
    for (i in 0 until min(m, 6)) {
        for (j in 0 until min(n, 6)) {
            System.out.printf("%12.5G", C[j + i * n])
        }
        println()
    }
    println()

    println("Example completed.\n")
    exitProcess(0)
}
