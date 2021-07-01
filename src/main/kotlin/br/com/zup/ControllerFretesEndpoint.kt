package br.com.zup

import br.com.zup.CalculaFreteRequest
import br.com.zup.CalculaFreteResponse
import br.com.zup.FretesGrpcServiceGrpc
import io.grpc.stub.StreamObserver
import kotlin.random.Random

class ControllerFretesEndpoint : FretesGrpcServiceGrpc.FretesGrpcServiceImplBase(){
    override fun calculaFrete(request: CalculaFreteRequest?, responseObserver: StreamObserver<CalculaFreteResponse>?) {


        val build =
            CalculaFreteResponse.newBuilder()
                .setCep(request?.cep)
                .setValor(Random.nextDouble(0.0, 190.0))
                .build()

        responseObserver?.onNext(build)
        responseObserver?.onCompleted()

    }
}