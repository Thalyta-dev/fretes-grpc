package br

import br.com.zup.ControllerFretesEndpoint
import io.grpc.ServerBuilder

fun main(){

    val server = ServerBuilder.forPort(8082).addService(ControllerFretesEndpoint()).build()

    server.start().awaitTermination()
}


